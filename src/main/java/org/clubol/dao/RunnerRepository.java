package org.clubol.dao;

import java.util.List;

import org.clubol.entity.Runner;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunnerRepository extends JpaRepository<Runner, Long> {

	List<Runner> findByLastName(String lastName);

	List<Runner> findByDocumentOrFirstNameOrLastName(String document,String firtsName, String lastName);

	Runner findByPosition(Long position);

}
