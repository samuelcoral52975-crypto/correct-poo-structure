package domain;

/**
 * EN: Represents a VIP customer with special discounts and membership level.
 * ES: Representa a un cliente VIP con descuentos especiales y un nivel de
 * membresía.
 */
public class VipCustomer extends Customer {

    // EN: Ensures compatibility during serialization.
    private static final long serialVersionUID = 1L;

    // ATTRIBUTES
    // ATRIBUTOS

    private double discount; 
    private String vipLevel; 

    // CONSTRUCTORS
    // CONSTRUCTORES

    /**
     * EN: Creates a VIP customer with a specified discount and level.
     */
    public VipCustomer(int id, String name, String email,
            double discount, String vipLevel) {
        super(id, name, email); 
        this.discount = discount;
        this.vipLevel = vipLevel;
    }

    /**
     * EN: Creates a VIP customer with default discount and level.
     */
    public VipCustomer(int id, String name, String email) {
        super(id, name, email);
        this.discount = 0.10; // EN: Default discount / ES: Descuento por defecto
        this.vipLevel = "GOLD"; // EN: Default level / ES: Nivel por defecto
    }

    // BUSINESS LOGIC
    // LÓGICA DEL NEGOCIO

    /**
     * EN: Computes the price applying the VIP discount.
     * ES: Calcula el precio aplicando el descuento VIP.
     */
    @Override
    public double computePrice(Event event) {
        return event.getTicketPrice() * (1 - discount);
    }

    // GETTERS

    public double getDiscount() {
        return discount;
    }

    public String getVipLevel() {
        return vipLevel;
    }
}