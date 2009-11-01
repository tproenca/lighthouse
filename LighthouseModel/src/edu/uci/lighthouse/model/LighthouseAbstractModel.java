package edu.uci.lighthouse.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nilmax
 */
public abstract class LighthouseAbstractModel {

	/** */
	private HashMap<String, LighthouseEntity> entities = new HashMap<String, LighthouseEntity>();

	/** */
	private HashMap<String, LinkedHashSet<LighthouseRelationship>> relationships = new HashMap<String, LinkedHashSet<LighthouseRelationship>>();

	// only ModelManager is allowed to call this method
	final synchronized void addEntity(LighthouseEntity entity) {
		entities.put(entity.getFullyQualifiedName(), entity);
	}
	
	// only ModelManager is allowed to call this method
	final synchronized void addRelationship(LighthouseRelationship rel) {
		LighthouseEntity e = rel.getToEntity();		
		LinkedHashSet<LighthouseRelationship> list = relationships.get(e
				.getFullyQualifiedName());		
		if (list == null){
			list = new LinkedHashSet<LighthouseRelationship>();
			relationships.put(e.getFullyQualifiedName(), list);
		}
		list.add(rel);
	}

	public boolean containsEntity(String fqn) {
		return (this.getEntity(fqn)!=null);
	}
	
	/** */
	public LighthouseEntity getEntity(String fqn) {
		return entities.get(fqn);
	}
	
	/** */
	public Collection<LighthouseEntity> getMethodsAndAttributesFromClass(
			LighthouseClass c) {
		LinkedList<LighthouseEntity> result = new LinkedList<LighthouseEntity>();
		Collection<LighthouseRelationship> list = relationships.get(c
				.getFullyQualifiedName());
		if (list != null){
			for (LighthouseRelationship r : list) {
				if (((r.getFromEntity() instanceof LighthouseField) || r
						.getFromEntity() instanceof LighthouseMethod)
						&& (r.getType() == LighthouseRelationship.TYPE.INSIDE)) {
					result.add(r.getFromEntity());
				}
			}
		}
		return result;
	}
	
	/** */
	public Collection<LighthouseClass> getAllClasses() {
		LinkedList<LighthouseClass> list = new LinkedList<LighthouseClass>();
		for (LighthouseEntity e : entities.values()) {
			if (e instanceof LighthouseClass) {
				list.add((LighthouseClass) e);
			}
		}
		return list;
	}
	
	/** */
	public Collection<LighthouseEntity> getEntities() {
		return entities.values();
	}
	
	public boolean containsRelationship(LighthouseRelationship relationship) {		
		LinkedHashSet<LighthouseRelationship> listOfAllRelationships = getRelationships();
		return listOfAllRelationships.contains(relationship);
	}
	
	/**
	 * Verify the <code>relationship</code> exist in the model
	 * @return the relationship instance from the model 
	 * */
	public LighthouseRelationship getRelationship(LighthouseRelationship relationship) {
		Collection<LighthouseRelationship> list = getRelationshipsFrom(relationship.getFromEntity());
		for (LighthouseRelationship lighthouseRelationship : list) {
			if (lighthouseRelationship.equals(relationship)) {
				return lighthouseRelationship;
			}
		}
		return null;
	}
	
	/** */
	public LinkedHashSet<LighthouseRelationship> getRelationships() {
		LinkedHashSet<LighthouseRelationship> result = new LinkedHashSet<LighthouseRelationship>();
		for (LinkedHashSet<LighthouseRelationship> list : relationships.values()) {			
			result.addAll(list);			
		}
		return result;
	}
	
//	public Collection<LighthouseRelationship> getRelationships(LighthouseEntity entity) {
//		List<LighthouseRelationship> result = new LinkedList<LighthouseRelationship>();
//		result.addAll(getRelationshipsFrom(entity));
//		result.addAll(getRelationshipsTo(entity));
//		return result;
//	}
	
	/** */
	public Collection<LighthouseRelationship> getRelationshipsFrom(LighthouseEntity e) {
		List<LighthouseRelationship> result = new LinkedList<LighthouseRelationship>();
		for (Collection<LighthouseRelationship> list : relationships.values()) {			
			for (LighthouseRelationship r : list) {			
				if (r.getFromEntity().getFullyQualifiedName().compareTo(e.getFullyQualifiedName()) == 0)
					result.add(r);			
			}
		}
		return result;
	}

//	public Collection<LighthouseRelationship> getRelationshipsTo(LighthouseEntity e) {
//		List<LighthouseRelationship> result = new LinkedList<LighthouseRelationship>();
//		Collection<LighthouseRelationship> list = relationships.get(e.getFullQualifiedName());
//		if (list != null)
//			result.addAll(list);
//		return result;
//	}
//	
//	public Collection<LighthouseEntity> getSubclasses(LighthouseEntity e) {
//		Collection<LighthouseEntity> result = new LinkedList<LighthouseEntity>();
//		Collection<LighthouseRelationship> list = this.getRelationshipsFrom(e);
//		for (LighthouseRelationship r : list)
//		{
//			if ( (r.getType() == TYPE.EXTENDS)
//					|| (r.getType() == TYPE.IMPLEMENTS) )
//			{
//				result.add(r.getToEntity());
//			}
//		}
//		return result;
//	}
//
//	public Collection<LighthouseEntity> getAllDescendants(LighthouseEntity e) {
//
//		Collection<LighthouseRelationship> list = this.getRelationshipsTo(e);
//		
//		Collection<LighthouseEntity> result = new LinkedList<LighthouseEntity>();
//		for (LighthouseRelationship r : list)
//		{
//			if ( (r.getType() == TYPE.EXTENDS)
//					|| (r.getType() == TYPE.IMPLEMENTS) )
//			{
//				result.add(r.getFromEntity());
//			}
//		}
//		
//		if (result.size() > 0)
//		{
//			Collection<LighthouseEntity> nextLevelDescendants = new LinkedList<LighthouseEntity>();
//			for (LighthouseEntity e2: result)
//			{
//				nextLevelDescendants.addAll(getAllDescendants(e2)); 
//			}
//			result.addAll(nextLevelDescendants);
//		}
//		
//		return result;
//	}
//
//	public LighthouseEntity getContainingClass (LighthouseEntity e)
//	{
//		Collection<LighthouseRelationship> list = this.getRelationshipsFrom(e);
//		
//		for (LighthouseRelationship r : list)
//		{
//			if  (r.getType() == TYPE.INSIDE)
//			{
//				return r.getToEntity();
//			}
//		}
//
//		return e;
//	}
	
}