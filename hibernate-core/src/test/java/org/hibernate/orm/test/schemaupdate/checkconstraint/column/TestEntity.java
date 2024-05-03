package org.hibernate.orm.test.schemaupdate.checkconstraint.column;

import java.util.List;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyDiscriminator;
import org.hibernate.annotations.AnyDiscriminatorValue;
import org.hibernate.annotations.AnyDiscriminatorValues;
import org.hibernate.annotations.AnyKeyJavaClass;

import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TEST_ENTITY")
public class TestEntity {
	@Id
	private Long id;

	@Column(
			name = "name_column",
			check = {
					@CheckConstraint(
							name = "column_constraint",
							constraint = ColumnCheckConstraintTest.COLUMN_CONSTRAINTS,
							options = "enforced"
					)
			})
	private String name;

	@OneToOne
	@JoinColumn(
			name = ColumnCheckConstraintTest.ONE_TO_ONE_JOIN_COLUMN_NAME,
			check = {
					@CheckConstraint(
							name = "ONE_TO_ONE_JOIN_COLUMN_CONSTRAINT",
							constraint = ColumnCheckConstraintTest.ONE_TO_ONE_JOIN_COLUMN_CONSTRAINTS
					)
			}
	)
	private AnotherTestEntity entity;

	@ManyToOne
	@JoinColumn(
			name = ColumnCheckConstraintTest.MANY_TO_ONE_JOIN_COLUMN_NAME,
			check = {
					@CheckConstraint(
							name = "MANY_TO_ONE_JOIN_COLUMN_CONSTRAINT",
							constraint = ColumnCheckConstraintTest.MANY_TO_ONE_JOIN_COLUMN_CONSTRAINTS
					)
			}
	)
	private AnotherTestEntity testEntity;

	@OneToMany
	@JoinColumn(
			name = ColumnCheckConstraintTest.ONE_TO_MANY_JOIN_COLUMN_NAME,
			check = {
					@CheckConstraint(
							name = "ONE_TO_MANY_JOIN_COLUMN_CONSTRAINT",
							constraint = ColumnCheckConstraintTest.ONE_TO_MANY_JOIN_COLUMN_CONSTRAINTS
					)
			}
	)
	private List<AnotherTestEntity> testEntities;

	@ManyToMany
	@JoinTable(
			name = "MANY_T0_MANY_TABLE",
			inverseJoinColumns = {
					@JoinColumn(
							name = ColumnCheckConstraintTest.MANY_TO_MANY_INVERSE_JOIN_COLUMN_NAME,
							check = {
									@CheckConstraint(
											name = "MANY_TO_MANY_INVERSE_JOIN_COLUMN_CONSTRAINT",
											constraint = ColumnCheckConstraintTest.MANY_TO_MANY_INVERSE_JOIN_COLUMN_CONSTRAINTS
									)
							}
					),
			},
			joinColumns = {
					@JoinColumn(
							name = ColumnCheckConstraintTest.MANY_TO_MANY_JOIN_COLUMN_NAME,
							check = {
									@CheckConstraint(
											name = "MANY_TO_MANY_JOIN_COLUMN_CONSTRAINT",
											constraint = ColumnCheckConstraintTest.MANY_TO_MANY_JOIN_COLUMN_CONSTRAINTS
									)
							}
					),
			}
	)
	private List<AnotherTestEntity> testEntities2;

	@Any
	@AnyDiscriminator(DiscriminatorType.STRING)
	@AnyDiscriminatorValues({
			@AnyDiscriminatorValue(discriminator = "S", entity = AnotherTestEntity.class),
	})
	@AnyKeyJavaClass(Long.class)
	@Column(name = "another_type")
	@JoinColumn(name = "another_id",
			check = {
					@CheckConstraint(
							name = "ANY_JOIN_COLUMN_CONSTRAINT",
							constraint = ColumnCheckConstraintTest.ANY_JOIN_COLUMN_CONSTRAINTS
					)
			})
	private Another another;
}
