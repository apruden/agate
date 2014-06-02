/*
 * Copyright (c) 2014 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.agate.service;

import javax.inject.Inject;

import org.obiba.agate.domain.Application;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSeed implements ApplicationListener<ContextRefreshedEvent> {

  @Inject
  private ApplicationService applicationService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    applicationService.save(new Application("opal", "opal"));
    applicationService.save(new Application("mica-server", "mica-server"));
    applicationService.save(new Application("mica-drupal", "mica-drupal"));
  }
}