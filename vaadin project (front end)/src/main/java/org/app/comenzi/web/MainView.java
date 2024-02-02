package org.app.comenzi.web;

import com.vaadin.flow.router.PageTitle;
import org.app.comenzi.web.views.continut.FormContinutView;
import org.app.comenzi.web.views.continut.NavigableGridContinuturiView;
import org.app.comenzi.web.views.utilizatori.FormUtilizatorView;
import org.app.comenzi.web.views.utilizatori.NavigableGridUtilizatoriView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

/**
 * The main view contains a button and a click listener.
 */
@Route @PageTitle("Platforma Streaming Online")
public class MainView extends VerticalLayout implements RouterLayout {

	public MainView() {
		setMenuBar();
	}

	private void setMenuBar() {
		MenuBar mainMenu = new MenuBar();
		MenuItem homeMenu = mainMenu.addItem("Home");
		
		homeMenu.addClickListener(event -> UI.getCurrent().navigate(MainView.class));

		//
		MenuItem gridFormsUtilizatoriMenu = mainMenu.addItem("Utilizatori");
		SubMenu gridFormsUtilizatoriMenuBar = gridFormsUtilizatoriMenu.getSubMenu();
		gridFormsUtilizatoriMenuBar.addItem("Lista Utilizator...",
				event -> UI.getCurrent().navigate(NavigableGridUtilizatoriView.class));
		gridFormsUtilizatoriMenuBar.addItem("Form Editare Utilizator...",
				event -> UI.getCurrent().navigate(FormUtilizatorView.class));
		
		//
		MenuItem gridFormsContinutMenu = mainMenu.addItem("Continut");
		SubMenu gridFormsContinutMenuBar = gridFormsContinutMenu.getSubMenu();
		gridFormsContinutMenuBar.addItem("Lista Continuturi...",
				event -> UI.getCurrent().navigate(NavigableGridContinuturiView.class));
		gridFormsContinutMenuBar.addItem("Form Editare Continut...",
				event -> UI.getCurrent().navigate(FormContinutView.class));
		
		//
		add(new HorizontalLayout(mainMenu));
	}
}
