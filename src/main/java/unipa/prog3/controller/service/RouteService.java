package unipa.prog3.controller.service;

import unipa.prog3.model.entity.Centro;
import unipa.prog3.model.entity.Route;
import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableProvider;

public class RouteService extends GenericService<Route> {
    public RouteService() {
        super(TableProvider.TableName.ROUTES);
    }

    @Override
    public Route entityFromString(String s) {
        String[] info = s.split(Table.delimiter);
        CenterService centerService = (CenterService) ServiceProvider.getService(Centro.class);
        Centro partenza = centerService.select(info[1]);
        Centro destinazione = centerService.select(info[2]);
        return new Route(info[0], partenza, destinazione);
    }

    @Override
    public String entityToString(Route route) {
        return route.getID() + Table.delimiter + route.getCentro1().getID()
                + Table.delimiter + route.getCentro2().getID();
    }
}