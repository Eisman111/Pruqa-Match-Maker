/*
 * Piggus - Copyright (c) 2018 by Paolo Amosso
 * License: GNU Affero General Public License v3.0
 */

/*
 * Here we manage the connection between the service and the db
 */

package com.pruqa.matchmakerauth.repository;

import com.pruqa.matchmakerauth.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Company findByCompanyEmail (String email);

    List<Company> findByActive(int activeStatus);
}
