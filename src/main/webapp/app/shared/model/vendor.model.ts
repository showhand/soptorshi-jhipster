import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';

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
    companyName?: string;
    description?: any;
    remarks?: VendorRemarks;
    vendorContactPeople?: IVendorContactPerson[];
}

export class Vendor implements IVendor {
    constructor(
        public id?: number,
        public companyName?: string,
        public description?: any,
        public remarks?: VendorRemarks,
        public vendorContactPeople?: IVendorContactPerson[]
    ) {}
}
