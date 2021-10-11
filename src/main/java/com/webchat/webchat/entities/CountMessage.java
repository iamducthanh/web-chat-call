package com.webchat.webchat.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedStoredProcedureQuery(name = "countMessage",
        procedureName = "count_message_by_month_and_year", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "month", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "year", type = String.class)})
public class CountMessage {
    @Id
    @Column
    Integer quantity;
}
