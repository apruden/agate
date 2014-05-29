/*
 * Copyright (c) 2014 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.agate.web.rest.ticket;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.obiba.agate.domain.Ticket;
import org.obiba.agate.domain.User;
import org.obiba.agate.service.UserService;
import org.obiba.agate.service.TicketService;
import org.obiba.web.model.AuthDtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@Path("/ticket/{ticket}")
public class TicketResource {

  private static final Logger log = LoggerFactory.getLogger(TicketResource.class);

  @PathParam("ticket")
  private String ticket;

  @Inject
  private TicketService ticketService;

  @Inject
  private UserService userService;

  @GET
  @Path("/subject")
  public AuthDtos.SubjectDto get() {
    Ticket ticket = ticketService.findById(this.ticket);
    AuthDtos.SubjectDto.Builder builder = AuthDtos.SubjectDto.newBuilder().setUsername(ticket.getUsername());
    User user = userService.findByUsername(ticket.getUsername());
    if (user != null) {
      builder.addAllGroups(user.getGroups());
    }
    return builder.build();
  }

  @GET
  @Path("/username")
  public Response getUsername() {
    Ticket ticket = ticketService.findById(this.ticket);
    return Response.ok().entity(ticket.getUsername()).build();
  }

  @DELETE
  public Response logout() {
    ticketService.delete(ticket);
    return Response.noContent().build();
  }

}

