package cn.edu.weizhou.cloudhomelink.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.edu.weizhou.cloudhomelink.domain.Book;

/**
 * <p>
 * BookDao class.
 * </p>
 * 
 * @author hanl
 * @version $Id: $Id
 */
@Repository
public class BookDao extends HibernateDaoSupport {
	private static final Logger LOGGER = Logger.getLogger(BookDao.class);

	/**
	 * <p>
	 * Constructor for BookDao.
	 * </p>
	 */
	public BookDao() {
	}

	/**
	 * <p>
	 * findById.
	 * </p>
	 * 
	 * @param id
	 *            a {@link java.lang.Integer} object.
	 * @return a {@link com.example.domain.Book} object.
	 */
	public Book findById(final Long id) {
		Assert.notNull(id);
		try {
			return this.getHibernateTemplate().get(Book.class, id);
		} catch (final Exception e) {
			BookDao.LOGGER.error(e);
			return null;
		}
	}

	/**
	 * <p>
	 * findAll.
	 * </p>
	 * 
	 * @return a {@link java.util.List} object.
	 */
	public List<Book> findAll() {
		return findAll(false, 0, 0);
	}

	/**
	 * <p>
	 * findAll.
	 * </p>
	 * 
	 * @param isPaging
	 *            a boolean.
	 * @param firstResult
	 *            a int.
	 * @param maxResults
	 *            a int.
	 * @return a {@link java.util.List} object.
	 */
	public List<Book> findAll(final boolean isPaging, final int firstResult,
			final int maxResults) {
		final String sql = "from Book ";
		List rst = null;
		rst = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List result = session.createQuery(sql)
						.setFirstResult(firstResult).setMaxResults(maxResults)
						.list();
				return result;
			}
		});
		return rst;
	}

	/**
	 * <p>
	 * remove.
	 * </p>
	 * 
	 * @param bookId
	 *            a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	@Transactional
	public boolean remove(final Long bookId) {
		final Book book0 = findById(bookId);
		if (book0 != null) {
			getHibernateTemplate().delete(book0);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * store.
	 * </p>
	 * 
	 * @param entity
	 *            a {@link com.example.domain.Book} object.
	 * @return a {@link com.example.domain.Book} object.
	 */
	@Transactional
	public Book store(final Book entity) {//create new book
		Long id = (Long)getHibernateTemplate().save(entity);
		return this.findById(id);
	}

	/**
	 * <p>
	 * save.
	 * </p>
	 * 
	 * @param entity
	 *            a {@link com.example.domain.Book} object.
	 */
	@Transactional
	public void save(final Book entity) {//not used?!
		getHibernateTemplate().update(entity);
	}

	@Transactional
	public void update(final Book entity) {
		getHibernateTemplate().update(entity);
	}

}
