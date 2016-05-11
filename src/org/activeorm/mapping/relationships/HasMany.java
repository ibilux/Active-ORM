package org.activeorm.mapping.relationships;

import org.activeorm.ActiveRecord;
import org.activeorm.exceptions.UnableToSaveException;
import org.activeorm.mapping.AttributeMapping;
import org.activeorm.query.Query;

import java.util.List;

/**
 * Created by Francis on 11/05/16.
 * Project Active-ORM.
 */
public class HasMany<T extends ActiveRecord> extends Relationship<T> {

    private List<T> cache;

    public HasMany(final AttributeMapping attribute) {
        super(attribute);
    }

    public Query<T> query() {
        return Query.build(type);
    }

    public List<T> results() {
        if (cache != null) {
            return cache;
        }
        return Query.build(type).results();
    }

    public boolean save() {
        if (cache == null) {
            return false;
        }
        for (final ActiveRecord record : cache) {
            if (!record.save()) {
                throw new UnableToSaveException("Was unable to save ActiveRecord");
            }
        }
        return true;
    }

    public boolean bind() {
        return false;
    }
}
