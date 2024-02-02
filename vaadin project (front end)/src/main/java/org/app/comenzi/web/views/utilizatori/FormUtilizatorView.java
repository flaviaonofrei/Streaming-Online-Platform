package org.app.comenzi.web.views.utilizatori;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.app.comenzi.web.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.oop.app.Utilizator;

@PageTitle("utilizator")
@Route(value = "utilizator", layout = MainView.class)
public class FormUtilizatorView extends VerticalLayout implements HasUrlParameter<Integer>{
	private static final long serialVersionUID = 1L;
	
	// Definire model date
	private EntityManager 	em;
	private Utilizator utilizator = null;
	private Binder<Utilizator> 	binder = new Binder<>(Utilizator.class);
	
	// Definire componente view
	// Definire Form-Master
	private VerticalLayout  formLayoutToolbar;
	private H1 				titluForm 	= new H1("Form Utilizator");
	private IntegerField 	idUtilizator 	= new IntegerField("ID Utilizator:");
	private TextField 		numeUtilizator = new TextField("Nume Utilizator: ");
	// Definire componente actiuni Form-Master-Controller
	private Button 			cmdAdaugare = new Button("Adauga");
	private Button 			cmdSterge 	= new Button("Sterge");
	private Button 			cmdAbandon 	= new Button("Abandon");
	private Button 			cmdSalveaza = new Button("Salveaza");
	
	// Start Form
	public FormUtilizatorView() {
		//
		initDataModel();
		//
		initViewLayout();
		//
		initControllerActions();
	}
	// Navigation Management
	@Override
	public void setParameter(BeforeEvent event, 
			@OptionalParameter Integer idUtilizator) {
		System.out.println("Utilizator ID: " + idUtilizator);
		if (idUtilizator != null) {
            // EDIT Item
        	this.utilizator = em.find(Utilizator.class, idUtilizator);
    		System.out.println("Selected utilizator to edit:: " + utilizator);
            if (this.utilizator == null) {
            	System.out.println("ADD utilizator:: " + utilizator);
	        	// NEW Item
	        	this.adaugaUtilizator();
	        	this.utilizator.setIdUtilizator(idUtilizator);
	        	this.utilizator.setNumeUtilizator("Utilizator NOU " + idUtilizator);
            }
        }
		this.refreshForm();
	}
	// init Data Model
	private void initDataModel(){ 
		System.out.println("DEBUG START FORM >>>  ");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
		this.em = emf.createEntityManager();		
		this.utilizator = em
				.createQuery("SELECT u FROM Utilizator u ORDER BY u.idUtilizator", Utilizator.class)
				.getResultStream().findFirst().get();
		
		//
		// binder.setBean(this.utilizator);
		binder.forField(idUtilizator).bind("idUtilizator");
		binder.forField(numeUtilizator).bind("numeUtilizator");
		//
		refreshForm();
	}	
	
	// init View Model 
	private void initViewLayout() {
		// Form-Master-Details -----------------------------------//
		// Form-Master
		FormLayout formLayout = new FormLayout();
		formLayout.add(idUtilizator, numeUtilizator);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
		formLayout.setMaxWidth("400px");
		// Toolbar-Actions-Master
		HorizontalLayout actionToolbar = 
				new HorizontalLayout(cmdAdaugare, cmdSterge, cmdAbandon, cmdSalveaza);
		actionToolbar.setPadding(false);
		
		// 
		//this.formLayoutToolbar = new VerticalLayout(formLayout, actionToolbar);
		// ---------------------------
		//this.add(titluForm, formLayoutToolbar, actionToolbar);
		this.add(titluForm, formLayout, actionToolbar);
		//
		this.idUtilizator.setEnabled(false);
	}

	// init Controller components
	private void initControllerActions() {
		// Transactional Master Actions
		cmdAdaugare.addClickListener(e -> {
			adaugaUtilizator();
			refreshForm();
		});
		cmdSterge.addClickListener(e -> {
			stergeUtilizator();
			// Navigate back to NavigableGridUtilizatoriForm
			this.getUI().ifPresent(ui -> ui.navigate(
					NavigableGridUtilizatoriView.class)
			);
		});
		cmdAbandon.addClickListener(e -> {
			// Navigate back to NavigableGridUtilizatoriForm
			this.getUI().ifPresent(ui -> ui.navigate(
					NavigableGridUtilizatoriView.class, this.utilizator.getIdUtilizator())
			);
		});
		cmdSalveaza.addClickListener(e -> {
			salveazaUtilizator();
			// refreshForm();
			// Navigate back to NavigableGridUtilizatoriForm
			this.getUI().ifPresent(ui -> ui.navigate(
					NavigableGridUtilizatoriView.class, this.utilizator.getIdUtilizator())
			);
		});
	}
	//
	private void refreshForm() {
		System.out.println("Utilizator curent: " + this.utilizator);
		if (this.utilizator != null) {
			binder.setBean(this.utilizator);
		}
	}

	// CRUD actions
	private void adaugaUtilizator() {
		this.utilizator = new Utilizator();
		this.utilizator.setIdUtilizator(999);
		this.utilizator.setNumeUtilizator("Utilizator Nou");
		//
		this.idUtilizator.setEnabled(true);
	}

	private void stergeUtilizator() {
		System.out.println("To remove: " + this.utilizator);
		if (this.em.contains(this.utilizator)) {
			try {
				this.em.getTransaction().begin();
				this.em.remove(this.utilizator);
				this.em.getTransaction().commit();
			} catch (Exception ex) {
				if (this.em.getTransaction().isActive())
					this.em.getTransaction().rollback();
				System.out.println("*** EntityManager Validation ex: " + ex.getMessage());
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	private void salveazaUtilizator() {
		this.idUtilizator.setEnabled(false);
		//
		try {
			this.em.getTransaction().begin();
			this.utilizator = this.em.merge(this.utilizator);
			this.em.getTransaction().commit();
			System.out.println("Utilizator Salvat");
		} catch (Exception ex) {
			if (this.em.getTransaction().isActive())
				this.em.getTransaction().rollback();
			System.out.println("*** EntityManager Validation ex: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
	}
}