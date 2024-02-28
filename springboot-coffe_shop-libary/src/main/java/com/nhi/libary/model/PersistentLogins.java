package com.nhi.libary.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {

	private String username;

	@Id
	private String series;

	private String token;

	private LocalDate last_used;
}
