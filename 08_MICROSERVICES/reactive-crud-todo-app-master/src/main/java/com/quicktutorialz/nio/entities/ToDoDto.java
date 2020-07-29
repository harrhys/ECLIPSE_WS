package com.quicktutorialz.nio.entities;

import java.util.Objects;

/**
 * @author alessandroargentieri
 *
 * This pojo is used to collect input data and create a ToDo
 */
public class ToDoDto {

    private String title;
    private String description;

    public ToDoDto() {
    }

    public ToDoDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToDoDto)) return false;
        ToDoDto toDoDto = (ToDoDto) o;
        return Objects.equals(getTitle(), toDoDto.getTitle()) &&
                Objects.equals(getDescription(), toDoDto.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription());
    }
}
