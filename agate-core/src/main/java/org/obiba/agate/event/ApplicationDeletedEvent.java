/*
 * Copyright (c) 2016 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.agate.event;

import org.obiba.agate.domain.Application;

public class ApplicationDeletedEvent extends PersistableDeletedEvent<Application> {
  public ApplicationDeletedEvent(Application application) {
    super(application);
  }
}
