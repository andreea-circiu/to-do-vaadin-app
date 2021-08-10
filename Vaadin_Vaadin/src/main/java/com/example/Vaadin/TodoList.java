package com.example.Vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
class TodoList extends VerticalLayout implements TodoChangeListener {
    @Autowired
    TodoRepository repository;
    private List<Todo> todos;
    Todo todo1= new Todo("Prepare presentation", true);
	Todo todo2= new Todo("Procrastinate", true);
	Todo todo3= new Todo("Have presentation", false);

    @PostConstruct
    void init() {
        setWidth("80%");
        update();
    }
    public List<Todo> getTodos(){
    	List<Todo> listTodos= new ArrayList<>();
    	Todo todo1= new Todo("Prepare presentation", true);
    	Todo todo2= new Todo("Procrastinate", true);
    	Todo todo3= new Todo("Have presentation", false);
    	listTodos.add(todo1);
    	listTodos.add(todo2);
    	listTodos.add(todo3);
    	return listTodos;
    }

    private void update() {
    	repository.save(todo1);
    	repository.save(todo2);
    	repository.save(todo3);
        setTodos(repository.findAll());
    }

    private void setTodos(List<Todo> todos) {
        this.todos = todos;
        removeAllComponents();
        todos.forEach(todo -> addComponent(new TodoLayout(todo, this)));
    }

     void addTodo(Todo todo) {
        repository.save(todo);
        update();
    }

    @Override
    public void todoChanged(Todo todo) {
        addTodo(todo);
    }


    public void deleteCompleted() {
    	repository.deleteInBatch(
    	    	todos.stream().filter(Todo::isDone).collect(Collectors.toList()));
        update();
    }
}

