/**
 * 
 */
package com.saleapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Alex
 *
 */
@NoRepositoryBean
public interface IGenericRepo<T, ID> extends JpaRepository<T, ID> {
}
