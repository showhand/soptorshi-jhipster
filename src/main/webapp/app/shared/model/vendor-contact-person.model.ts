export interface IVendorContactPerson {
    id?: number;
    name?: string;
    designation?: string;
    contactNumber?: string;
    vendorId?: number;
}

export class VendorContactPerson implements IVendorContactPerson {
    constructor(
        public id?: number,
        public name?: string,
        public designation?: string,
        public contactNumber?: string,
        public vendorId?: number
    ) {}
}
