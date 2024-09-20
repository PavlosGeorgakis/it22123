import java.util.List;

public class CooperativeDto {
    private String name;
    private String vat;
    private List<UserDto> farmers;
    private List<ProductDto> products;
    private List<CultivationLocationDto> cultivationLocations;

    // Ιδιωτικός constructor
    private CooperativeDto(Builder builder) {
        this.name = builder.name;
        this.vat = builder.vat;
        this.farmers = builder.farmers;
        this.products = builder.products;
        this.cultivationLocations = builder.cultivationLocations;
    }

    public String getName() {
        return name;
    }

    public String getVat() {
        return vat;
    }

    public List<UserDto> getFarmers() {
        return farmers;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public List<CultivationLocationDto> getCultivationLocations() {
        return cultivationLocations;
    }

    // Ο Builder class
    public static class Builder {
        private String name;
        private String vat;
        private List<UserDto> farmers;
        private List<ProductDto> products;
        private List<CultivationLocationDto> cultivationLocations;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setVat(String vat) {
            this.vat = vat;
            return this;
        }

        public Builder setFarmers(List<UserDto> farmers) {
            this.farmers = farmers;
            return this;
        }

        public Builder setProducts(List<ProductDto> products) {
            this.products = products;
            return this;
        }

        public Builder setCultivationLocations(List<CultivationLocationDto> cultivationLocations) {
            this.cultivationLocations = cultivationLocations;
            return this;
        }

        public CooperativeDto build() {
            return new CooperativeDto(this);
        }
    }
}

