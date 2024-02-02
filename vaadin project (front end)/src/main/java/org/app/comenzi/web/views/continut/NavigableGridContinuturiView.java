package org.app.comenzi.web.views.continut;

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
import org.oop.app.Continut;

@PageTitle("continuturi")
@Route(value = "continuturi", layout = MainView.class)
public class NavigableGridContinuturiView extends VerticalLayout implements HasUrlParameter<Integer>{
	private static final long serialVersionUID = 1L;
	
	// Definire model date
	private EntityManager em;
	private List<Continut> 	continuturi = new ArrayList<>();
	private Continut 			continut = null;
	
	// Definire componente view
	private H1 				titluForm 	= new H1("Lista Continuturi");
	// Definire componente suport navigare
	private VerticalLayout  gridLayoutToolbar;
	private TextField 		filterText = new TextField();
	private Button 			cmdEditContinut = new Button("Editeaza continut...");
	private Button 			cmdAdaugaContinut = new Button("Adauga continut...");
	private Button 			cmdStergeContinut = new Button("Sterge continut");
	private Grid<Continut> 	grid = new Grid<>(Continut.class);
	
	// Start Form
	public NavigableGridContinuturiView() {
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
		if (idContinut != null) {
        	this.continut = em.find(Continut.class, idContinut);
    		System.out.println("Back continut: " + continut);
    		if (this.continut == null) {
            	// DELETED Item
            	if (!this.continuturi.isEmpty())
            		this.continut = this.continuturi.get(0);
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
		
		List<Continut> lst = em
				.createQuery("SELECT p FROM Continut p ORDER BY p.idContinut", Continut.class)
				.getResultList();
		continuturi.addAll(lst);
		
		if (lst != null && !lst.isEmpty()){
			Collections.sort(this.continuturi, (p1, p2) -> p1.getIdContinut().compareTo(p2.getIdContinut()));
			this.continut = continuturi.get(0);
			System.out.println("DEBUG: continut init >>> " + continut.getIdContinut());
		}
		//
		grid.setItems(this.continuturi);
		grid.asSingleSelect().setValue(this.continut);
	}	
	
	// init View Model 
	private void initViewLayout() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Layout navigare -------------------------------------//
		// Toolbar navigare
		filterText.setPlaceholder("Filter by titluContinut...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		HorizontalLayout gridToolbar = new HorizontalLayout(filterText, 
				cmdEditContinut, cmdAdaugaContinut, cmdStergeContinut);
		// Grid navigare
		grid.setColumns("IdContinut", "titluContinut", "durataContinut");
		grid.addComponentColumn(item -> createGridActionsButtons(item)).setHeader("Actiuni");
		// Init Layout navigare
		gridLayoutToolbar = new VerticalLayout(gridToolbar, grid);
		// ---------------------------
		this.add(titluForm, gridLayoutToolbar);
		//
	}

	// init Controller components
	private void initControllerActions() {
		// Navigation Actions
		filterText.addValueChangeListener(e -> updateList());
		cmdEditContinut.addClickListener(e -> {
			editContinut();
		});
		cmdAdaugaContinut.addClickListener(e -> {
			adaugaContinut();
		});
		cmdStergeContinut.addClickListener(e -> {
			stergeContinut();
			refreshForm();
		});
	}
	
	//
	private Component createGridActionsButtons(Continut item) {
		//
		Button cmdEditItem = new Button("Edit");
		cmdEditItem.addClickListener(e -> {
					grid.asSingleSelect().setValue(item);
					editContinut();
		});
		Button cmdDeleteItem = new Button("Sterge");
		cmdDeleteItem.addClickListener(e -> {
					System.out.println("Sterge item: " + item);
					grid.asSingleSelect().setValue(item);
					stergeContinut();
					refreshForm();
		}	);
		//
		return new HorizontalLayout(cmdEditItem, cmdDeleteItem);
	}
	//
	private void editContinut() {
		this.continut = this.grid.asSingleSelect().getValue();
		System.out.println("Selected continut:: " + continut);
		if (this.continut != null) {
//			this.getUI().ifPresent(ui -> ui.navigate(
//					FormContinutView.class, this.continut.getIdContinut())
//			);
			UI.getCurrent().navigate(FormContinutView.class, this.continut.getIdContinut());
        } 
	}
	//
	private void updateList() { 
        try {
        	List<Continut> lstContinuturiFiltrate = this.continuturi;
        	
        	if (filterText.getValue() != null) {
        		lstContinuturiFiltrate = this.continuturi.stream()
        			.filter(p -> p.getTitluContinut().contains(filterText.getValue()))
        			.toList();
        	
        		grid.setItems(lstContinuturiFiltrate);
        	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//
	private void refreshForm() {
		System.out.println("Continut curent: " + this.continut);
		if (this.continut != null) {
			grid.setItems(this.continuturi);
			grid.select(this.continut);
		}
	}
	
	// CRUD actions
	private void adaugaContinut() {
		this.getUI().ifPresent(ui -> ui.navigate(FormContinutView.class, 999));
	}

	private void stergeContinut() {
		this.continut = this.grid.asSingleSelect().getValue();
		System.out.println("To remove: " + this.continut);
		this.continuturi.remove(this.continut);
		if (this.em.contains(this.continut)) {
			this.em.getTransaction().begin();
			this.em.remove(this.continuturi);
			this.em.getTransaction().commit();
		}

		if (!this.continuturi.isEmpty())
			this.continut = this.continuturi.get(0);
		else
			this.continut = null;
	}
}