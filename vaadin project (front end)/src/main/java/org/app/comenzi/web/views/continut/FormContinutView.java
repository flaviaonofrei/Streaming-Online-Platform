package org.app.comenzi.web.views.continut;

import java.text.SimpleDateFormat;

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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.oop.app.Continut;

@PageTitle("continut")
@Route(value = "continut", layout = MainView.class)
public class FormContinutView extends VerticalLayout implements HasUrlParameter<Integer>{
	private static final long serialVersionUID = 1L;
	
	// Definire model date
	private EntityManager 	em;
	private Continut continut = null;
	private Binder<Continut> 	binder = new BeanValidationBinder<>(Continut.class);
	
	// Definire componente view
	// Definire Form-Master
	private VerticalLayout  formLayoutToolbar;
	private H1 				titluForm 	= new H1("Form Continut");
	private IntegerField 	IdContinut 	= new IntegerField("Cod Continut:");
	private TextField 		titluContinut = new TextField("Titlu Continut: ");
	private NumberField 	durataContinut = new NumberField("Durata Continut: ");
	// Definire componente actiuni Form-Master-Controller
	private Button 			cmdAdaugare = new Button("Adauga");
	private Button 			cmdSterge 	= new Button("Sterge");
	private Button 			cmdAbandon 	= new Button("Abandon");
	private Button 			cmdSalveaza = new Button("Salveaza");
	
	// Start Form
	public FormContinutView() {
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
			@OptionalParameter Integer idContinut) {
		System.out.println("Continut ID: " + idContinut);
		if (idContinut != null) {
            // EDIT Item
        	this.continut = em.find(Continut.class, idContinut);
    		System.out.println("Selected continut to edit:: " + continut);
            if (this.continut == null) {
            	System.out.println("ADD continut:: " + continut);
	        	// NEW Item
	        	this.adaugaContinut();
	        	this.continut.setIdContinut(idContinut);
	        	this.continut.setTitluContinut("Continut NOU " + idContinut);
            }
        }
		this.refreshForm();
	}
	// init Data Model
	private void initDataModel(){ 
		System.out.println("DEBUG START FORM >>>  ");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
		this.em = emf.createEntityManager();		
		this.continut = em
				.createQuery("SELECT p FROM Continut p ORDER BY p.idContinut", Continut.class)
				.getResultStream().findFirst().get();
		
		//

		//
		binder.bind(IdContinut, "IdContinut");
		binder.bind(titluContinut, "TitluContinut");
		binder.bind(durataContinut, "durataContinut");
		//
		refreshForm();
	}	
	
	// init View Model 
	private void initViewLayout() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Form-Master-Details -----------------------------------//
		// Form-Master
		FormLayout formLayout = new FormLayout();
		formLayout.add(IdContinut, titluContinut, durataContinut);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
		formLayout.setMaxWidth("400px");
		// Toolbar-Actions-Master
		HorizontalLayout actionToolbar = 
				new HorizontalLayout(cmdAdaugare, cmdSterge, cmdAbandon, cmdSalveaza);
		actionToolbar.setPadding(false);
		
		// 
		this.formLayoutToolbar = new VerticalLayout(formLayout, actionToolbar);
		// ---------------------------
		this.add(titluForm, formLayoutToolbar);
		//
		this.IdContinut.setEnabled(false);
	}

	// init Controller components
	private void initControllerActions() {
		// Transactional Master Actions
		cmdAdaugare.addClickListener(e -> {
			adaugaContinut();
			refreshForm();
		});
		cmdSterge.addClickListener(e -> {
			stergeContinut();
			// Navigate back to NavigableGridContinuturiForm
			this.getUI().ifPresent(ui -> ui.navigate(
					NavigableGridContinuturiView.class)
			);
		});
		cmdAbandon.addClickListener(e -> {
			// Navigate back to NavigableGridContinuturiForm
			this.getUI().ifPresent(ui -> ui.navigate(
					NavigableGridContinuturiView.class, this.continut.getIdContinut())
			);
		});
		cmdSalveaza.addClickListener(e -> {
			salveazaContinut();
			// refreshForm();
			// Navigate back to NavigableGridContinutForm
			this.getUI().ifPresent(ui -> ui.navigate(
					NavigableGridContinuturiView.class, this.continut.getIdContinut())
			);
		});
	}
	//
	private void refreshForm() {
		System.out.println("Continut curent: " + this.continut);
		if (this.continut != null) {
			binder.setBean(this.continut);
		}
	}

	// CRUD actions
	private void adaugaContinut() {
		this.continut = new Continut();
		this.continut.setIdContinut(999);
		this.continut.setTitluContinut("Continut Nou");
		//
		this.IdContinut.setEnabled(true);
	}

	private void stergeContinut() {
		System.out.println("To remove: " + this.continut);
		if (this.em.contains(this.continut)) {
			this.em.getTransaction().begin();
			this.em.remove(this.continut);
			this.em.getTransaction().commit();
		}
	}

	private void salveazaContinut() {
		this.IdContinut.setEnabled(false);
		//
		try {
			this.em.getTransaction().begin();
			this.continut = this.em.merge(this.continut);
			this.em.getTransaction().commit();
			System.out.println("Continut Salvat");
		} catch (Exception ex) {
			if (this.em.getTransaction().isActive())
				this.em.getTransaction().rollback();
			System.out.println("*** EntityManager Validation ex: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
	}
}