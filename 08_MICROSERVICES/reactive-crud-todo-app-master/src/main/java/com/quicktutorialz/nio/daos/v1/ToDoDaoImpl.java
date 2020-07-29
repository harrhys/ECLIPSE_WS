package com.quicktutorialz.nio.daos.v1;


import com.quicktutorialz.nio.entities.ToDo;
import com.quicktutorialz.nio.entities.ToDoDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author alessandroargentieri
 *
 * Implementation of a fake blocking database of ToDos
 */
public class ToDoDaoImpl implements ToDoDao{

    Map<String, ToDo> toDos;

    /* singleton implementation */
    private static ToDoDao instance;
    public static ToDoDao getInstance(){
        return (instance !=null) ? instance : new ToDoDaoImpl() ;
    }
    private ToDoDaoImpl(){
        initializeDB();
    }

    @Override
    public ToDo create(ToDoDto dto) {
        ToDo todo = new ToDo(dto.getTitle(), dto.getDescription());
        toDos.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public Optional<ToDo> read(String id) {
        return Optional.ofNullable(toDos.get(id));
    }

    @Override
    public List<ToDo> readAll() {
        return toDos.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }

    @Override
    public Optional<ToDo> update(ToDo toDo) {
        if(toDos.get(toDo.getId())!=null){
            toDos.replace(toDo.getId(), toDo);
            return Optional.of(toDos.get(toDo.getId()));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Boolean delete(String id) {
        if(toDos.get(id)!=null) {
            toDos.remove(id);
            return true;
        }
        return false;
    }

    private void initializeDB(){
        toDos = new HashMap<>();
        ToDo todo1 = new ToDo("study reactive", "learn reactive programming");
        toDos.put(todo1.getId(), todo1);
        ToDo todo2 = new ToDo("learn ReactiveJ", "learn to use ReactiveJ library");
        toDos.put(todo2.getId(), todo2);
        ToDo todo3 = new ToDo("exercise", "do some exercises");
        toDos.put(todo3.getId(), todo3);
    }

}
