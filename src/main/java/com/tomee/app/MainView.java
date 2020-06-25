package com.tomee.app;

import com.tomee.app.ejb.CustomerFacade;
import com.tommee.app.modelo.Customer;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 * The main view contains a simple label element and a template element.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow with CDI", shortName = "Project Base", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    @Inject
    private GreetService greetService;
    @EJB
    private CustomerFacade customerService;
    
    private final Grid<Customer> gridCustomer;
    
    
    
    public MainView() {
        this.gridCustomer = new Grid<>(Customer.class);
        
        TextField textField = new TextField("Your name is as ");
        textField.addThemeName("bordered");
        
        setupGrid();
        

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Saludar desde  CDI e Invocar a EJB 3 Para Datos " );
        
        button.addClickListener(
        (t) -> {
                 Notification.show(greetService.greet(textField.getValue()));
                 gridCustomer.setItems(customerService.findAll());
                 
                }
        );

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(textField, button, gridCustomer);
        
    }
    
    

    @PostConstruct
    public void init() {
        // Use TextField for standard text input
        Notification.show("Post Event");
    }

    private void setupGrid() {
        gridCustomer.removeAllColumns();
        gridCustomer.addColumn(Customer::getName).setHeader("Cliente");
        
    }

}
