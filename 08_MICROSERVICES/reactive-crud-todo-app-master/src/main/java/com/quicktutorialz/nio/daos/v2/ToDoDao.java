package com.quicktutorialz.nio.daos.v2;

import com.quicktutorialz.nio.entities.ToDo;
import com.quicktutorialz.nio.entities.ToDoDto;
import io.reactivex.Observable;

import java.util.List;
import java.util.Optional;

/**
 * @author alessandroargentieri
 *
 * This layer represent the connection to a Non-Blocking Database
 */
public interface ToDoDao {
    Observable<ToDo> create(ToDoDto dto);
    Observable<Optional<ToDo>> read(String id);
    Observable<List<ToDo>> readAll();
    Observable<Optional<ToDo>> update(ToDo toDo);
    Observable<Boolean> delete(String id);
}
