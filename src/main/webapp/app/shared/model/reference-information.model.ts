export interface IReferenceInformation {
    id?: number;
    name?: string;
    designation?: string;
    organization?: string;
    contactNumber?: string;
    employeeId?: number;
}

export class ReferenceInformation implements IReferenceInformation {
    constructor(
        public id?: number,
        public name?: string,
        public designation?: string,
        public organization?: string,
        public contactNumber?: string,
        public employeeId?: number
    ) {}
}
