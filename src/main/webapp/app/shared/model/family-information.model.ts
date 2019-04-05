export interface IFamilyInformation {
    id?: number;
    name?: string;
    relation?: string;
    contactNumber?: string;
    employeeId?: number;
}

export class FamilyInformation implements IFamilyInformation {
    constructor(
        public id?: number,
        public name?: string,
        public relation?: string,
        public contactNumber?: string,
        public employeeId?: number
    ) {}
}
