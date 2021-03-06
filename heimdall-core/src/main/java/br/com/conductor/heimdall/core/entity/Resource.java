
package br.com.conductor.heimdall.core.entity;

/*-
 * =========================LICENSE_START==================================
 * heimdall-core
 * ========================================================================
 * Copyright (C) 2018 Conductor Tecnologia SA
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ==========================LICENSE_END===================================
 */

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.google.common.collect.Lists;
import com.google.common.collect.Lists;

import br.com.twsoftware.alfred.object.Objeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class represents a Resource registered to the system.
 * 
 * @author Filipe Germano
 *
 */
@Data
@Table(name = "RESOURCES", uniqueConstraints = { @UniqueConstraint(columnNames = { "API_ID", "NAME" }) })
@Entity
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
public class Resource implements Serializable {

     private static final long serialVersionUID = 8482072044625354477L;

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ID")
     private Long id;

     @Column(name = "NAME", length = 180, unique = true, nullable = false)
     private String name;

     @Column(name = "DESCRIPTION", length = 200)
     private String description;

     @JsonIgnore
     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "API_ID", nullable = false)
     private Api api;

     @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE }, orphanRemoval = true, mappedBy = "resource")
     @JsonBackReference
     private List<Operation> operations;

     @JsonIgnore
     @Column(name = "TAGS", length = 2000)
     private String tag;

     @Transient
     private List<String> tags;

     @PostLoad
     private void loadMethods() {

          if (Objeto.notBlank(tag)) {

               tags = Lists.newArrayList(tag.split(";"));
               // tags = Arrays.asList(tag.split(";"));
          }
     }

}
