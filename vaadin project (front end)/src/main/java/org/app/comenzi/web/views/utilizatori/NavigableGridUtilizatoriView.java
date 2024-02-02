package org.app.comenzi.web.views.utilizatori;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.flow.component.UI;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.app.comenzi.web.MainView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.oop.app.Utilizator;

@PageTitle("utilizatori")
@Route(value = "utilizatori", layout = MainView.class)
public class NavigableGridUtilizatoriView extends VerticalLayout implements HasUrlParameter<Integer>{
	private static final long serialVersionUID = 1L;
	
	// Definire model date
	private EntityManager em;
	private List<Utilizator> 	utilizatori = new ArrayList<>();
	private Utilizator 			utilizator = null;
	
	// Definire componente view
	private H1 				titluForm 	= new H1("Lista utilizatori");
	// Definire componente suport navigare
	private VerticalLayout  gridLayoutToolbar;
	private TextField 		filterText = new TextField();
	private Button 			cmdEditUtilizator = new Button("Editeaza utilizator...");
	private Button 			cmdAdaugaUtilizator = new Button("Adauga utilizator...");
	private Button 			cmdStergeUtilizator = new Button("Sterge utilizator");
	private Grid<Utilizator> 	grid = new Grid<>(Utilizator.class);
	
	// Start Form
	public NavigableGridUtilizatoriView() {
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
		if (idUtilizator != null) {
        	this.utilizator = em.find(Utilizator.class, idUtilizator);
    		System.out.println("Back utilizator: " + utilizator);
    		if (this.utilizator == null) {
            	// DELETED Item
            	if (!this.utilizatori.isEmpty())
            		this.utilizator = this.utilizatori.get(0);
            }
    		// else: EDITED or NEW Item
        }
		this.refreshForm();
		
	}
	// init Data Model
	private void initDataModel(){ 
		System.out.println("DEBUG START FORM >>>  ");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
		em = emf.createEntityManager();
		
		List<Utilizator> lst = em
				.createQuery("SELECT u FROM Utilizator u ORDER BY u.idUtilizator", Utilizator.class)
				.getResultList();
		this.utilizatori.addAll(lst);
		
		if (lst != null && !lst.isEmpty()){
			Collections.sort(this.utilizatori, (c1, c2) -> c1.getIdUtilizator().compareTo(c2.getIdUtilizator()));
			this.utilizator = utilizatori.get(0);
			System.out.println("DEBUG: utilizator init >>> " + utilizator.getIdUtilizator());
		}
		//
		grid.setItems(this.utilizatori);
		grid.asSingleSelect().setValue(this.utilizator);
	}	
	
	// init View Model 
	private void initViewLayout() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Layout navigare -------------------------------------//
		// Toolbar navigare
		filterText.setPlaceholder("Filter by nume...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		HorizontalLayout gridToolbar = new HorizontalLayout(filterText, 
				cmdEditUtilizator, cmdAdaugaUtilizator, cmdStergeUtilizator);
		// Grid navigare
		grid.setColumns("idUtilizator", "numeUtilizator");
		grid.addComponentColumn(item -> createGridActionsButtons(item)).setHeader("Actiuni");
		// Init Layout navigare
		//gridLayoutToolbar = new VerticalLayout(gridToolbar, grid);
		// ---------------------------
		this.add(titluForm, gridToolbar, grid);
		//
	}

	// init Controller components
	private void initControllerActions() {
		// Navigation Actions
		filterText.addValueChangeListener(e -> updateList());
		cmdEditUtilizator.addClickListener(e -> editUtilizator() );
		cmdAdaugaUtilizator.addClickListener(e -> adaugaUtilizator() );
		cmdStergeUtilizator.addClickListener(e -> {
			stergeUtilizator();
			refreshForm();
		});
	}
	
	//
	private Component createGridActionsButtons(Utilizator item) {
		//
		Button cmdEditItem = new Button("Edit");
		cmdEditItem.addClickListener(e -> {
					grid.asSingleSelect().setValue(item);
					editUtilizator();
		});
		Button cmdDeleteItem = new Button("Sterge");
		cmdDeleteItem.addClickListener(e -> {
					System.out.println("Sterge item: " + item);
					grid.asSingleSelect().setValue(item);
					stergeUtilizator();
					refreshForm();
		}	);
		//
		return new HorizontalLayout(cmdEditItem, cmdDeleteItem);
	}
	//
	private void editUtilizator() {
		this.utilizator = this.grid.asSingleSelect().getValue();
		System.out.println("Selected utilizator:: " + utilizator);
		if (this.utilizator != null) {
			//this.getUI().ifPresent( ui -> ui.navigate(FormUtilizatorView.class, this.utilizator.getId()) );
			UI.getCurrent().navigate(FormUtilizatorView.class, this.utilizator.getIdUtilizator());
        } 
	}
	//
	private void updateList() { 
        try {
        	List<Utilizator> lstUtilizatoriFiltrati = this.utilizatori;
        	
        	if (filterText.getValue() != null) {
        		lstUtilizatoriFiltrati = this.utilizatori.stream()
        			.filter(c -> c.getNumeUtilizator().contains(filterText.getValue()))
        			.toList();
        	
        		grid.setItems(lstUtilizatoriFiltrati);
        	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//
	private void refreshForm() {
		System.out.println("Utilizator curent: " + this.utilizator);
		if (this.utilizator != null) {
			grid.setItems(this.utilizatori);
			grid.select(this.utilizator);
		}
	}
	
	// CRUD actions
	private void adaugaUtilizator() {
		this.getUI().ifPresent(ui -> ui.navigate(FormUtilizatorView.class, 999));
	}

	private void stergeUtilizator() {
		this.utilizator = this.grid.asSingleSelect().getValue();
		System.out.println("To remove: " + this.utilizator);
		this.utilizatori.remove(this.utilizator);
		if (this.em.contains(this.utilizator)) {
			this.em.getTransaction().begin();
			this.em.remove(this.utilizator);
			this.em.getTransaction().commit();
		}

		if (!this.utilizatori.isEmpty())
			this.utilizator = this.utilizatori.get(0);
		else
			this.utilizator = null;
	}
}