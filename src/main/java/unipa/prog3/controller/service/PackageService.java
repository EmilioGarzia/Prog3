package unipa.prog3.controller.service;

import unipa.prog3.model.entity.Cliente;
import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.entity.Veicolo;
import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableProvider;

import java.util.Vector;

public class PackageService extends GenericService<Collo> {
    public PackageService() {
        super(TableProvider.TableName.PACKAGES);
    }

    public Vector<Collo> selectNotSent() {
        return select(collo -> collo.getVeicolo() == null);
    }

    public Vector<Collo> selectByVehicle(Veicolo veicolo) {
        return select(collo -> collo.getVeicolo().getID().equals(veicolo.getID()));
    }

    @Override
    public Collo entityFromString(String s) {
        String[] info = s.split(Table.delimiter);

        ClientService clientService = (ClientService) ServiceProvider.getService(Cliente.class);
        Cliente mittente = clientService.select(info[1]);
        Cliente destinatario = clientService.select(info[2]);

        float peso = Float.parseFloat(info[3]);
        boolean consegnato = Boolean.parseBoolean(info[4]);

        Veicolo veicolo = null;
        if (!info[4].equals("null")) {
            VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
            veicolo = vehicleService.select(info[5]);
        }

        return new Collo(info[0], mittente, destinatario, peso, consegnato, veicolo);
    }

    @Override
    public String entityToString(Collo collo) {
        StringBuilder builder = new StringBuilder();
        builder.append(collo.getID()).append(Table.delimiter).append(collo.getMittente().getID())
                .append(Table.delimiter).append(collo.getDestinatario().getID()).append(Table.delimiter)
                .append(collo.getPeso()).append(Table.delimiter).append(collo.isConsegnato()).append(Table.delimiter);
        if (collo.getVeicolo() != null)
            builder.append(collo.getVeicolo().getID());
        else builder.append("null");
        return builder.toString();
    }
}
