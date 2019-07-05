export const enum VendorRemarks {
    VERY_GOOD = 'VERY_GOOD',
    GOOD = 'GOOD',
    MODERATE = 'MODERATE',
    AVERAGE = 'AVERAGE',
    BELOW_AVERAGE = 'BELOW_AVERAGE',
    BAD = 'BAD'
}

export interface IVendor {
    id?: number;
    vendorId?: number;
    companyName?: string;
    description?: any;
    remarks?: VendorRemarks;
}

export class Vendor implements IVendor {
    constructor(
        public id?: number,
        public vendorId?: number,
        public companyName?: string,
        public description?: any,
        public remarks?: VendorRemarks
    ) {
        this.vendorId = id;
    }
}
