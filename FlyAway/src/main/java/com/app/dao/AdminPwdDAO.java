package com.app.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.app.entity.AdminPwd;
import com.app.util.HybernateUtil;

public class AdminPwdDAO {
	
	public static String getAdminPwd() {
		String pwdStr = "";
		
		Session session = null;
		
		try {
			session = HybernateUtil.getSessionFactory().openSession();
			String queryStr = "FROM AdminPwd";
			TypedQuery<AdminPwd> query = session.createQuery(queryStr);
			
			AdminPwd pwdResult = query.getSingleResult();
			
			pwdStr = pwdResult.getPwd();
			
			session.close();
			
		} catch (NoResultException e) {
			//There should have been a result
			//since there wasn't, we will insert the default password back into the DB
			setupDefaultPassword();
			return "admin";
		} catch (NonUniqueResultException e) {
			//there are multiple passwords in the DB, delete them and reset to default password
			String queryStr = "FROM AdminPwd";
			TypedQuery<AdminPwd> query = session.createQuery(queryStr);
			
			List<AdminPwd> passwords = query.getResultList();
			
			for (AdminPwd adminPwd : passwords) {
				deletePassword(adminPwd);
			}
			setupDefaultPassword();
			
			return "admin";
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return pwdStr;
	}
	
	public static boolean insertPassword(AdminPwd pwd) {
		boolean success = false;
		Session session = null;
		
		try {
			session= HybernateUtil.getSessionFactory().openSession();
			
			//remove old password
			String queryStr = "FROM AdminPwd";
			TypedQuery<AdminPwd> query = session.createQuery(queryStr);
			List<AdminPwd> passwords = query.getResultList();
			for (AdminPwd adminPwd : passwords) {
				deletePassword(adminPwd);
			}
			
			//persist new password
			session.beginTransaction();
			session.persist(pwd);
			session.getTransaction().commit();
			session.close();
			
			success=true;
		} catch (HibernateException e) {
			// TODO: handle exception
			//there was some error opening the session
		} catch (RollbackException ex) {
			//TODO: handle exception
			//there was an error while committing to the DB
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return success;
	}	
	
	public static boolean deletePassword(AdminPwd pwd) {
		boolean success = false;
		Session session = null;
		
		try {
			session = HybernateUtil.getSessionFactory().openSession();
			
			//make sure it actually exists in the DB
			AdminPwd tempPwd = session.find(AdminPwd.class, pwd.getId());
			
			if(tempPwd != null) {
				session.beginTransaction();
				session.remove(tempPwd);
				session.getTransaction().commit();
				success = true;
				session.close();
			}
		} catch (IllegalArgumentException e) {
			//there was some issue during find operation, id was probably invalid
		} catch (Exception e) {
			// TODO: handle exception, assume the delete failed
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return success;
	}
	
	private static void setupDefaultPassword() {
		Session session = null;
		try {
			session = HybernateUtil.getSessionFactory().openSession();
			AdminPwd defaultPwd = new AdminPwd();
			defaultPwd.setPwd("admin");
			session.beginTransaction();
			session.persist(defaultPwd);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			//TODO: handle Exceptions
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
}
