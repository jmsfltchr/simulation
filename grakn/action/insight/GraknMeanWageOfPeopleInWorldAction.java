/*
 * Copyright (C) 2020 Grakn Labs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package grakn.simulation.grakn.action.insight;

import grakn.simulation.common.action.insight.MeanWageOfPeopleInWorldAction;
import grakn.simulation.grakn.driver.GraknOperation;
import graql.lang.Graql;
import graql.lang.query.GraqlGet;
import graql.lang.statement.Statement;

import static grakn.simulation.grakn.action.Model.WAGE;
import static grakn.simulation.grakn.action.Model.WAGE_VALUE;

public class GraknMeanWageOfPeopleInWorldAction extends MeanWageOfPeopleInWorldAction<GraknOperation> {
    public GraknMeanWageOfPeopleInWorldAction(GraknOperation dbOperation) {
        super(dbOperation);
    }

    @Override
    public Double run() {
        return dbOperation.execute(query()).doubleValue();
    }

    public static GraqlGet.Aggregate query() {
        Statement wageValue = Graql.var(WAGE_VALUE);
        return Graql.match(
                Graql.var(WAGE).isa(WAGE)
                        .has(WAGE_VALUE, wageValue)
        ).get(wageValue.var()).mean(wageValue.var());
    }
}
