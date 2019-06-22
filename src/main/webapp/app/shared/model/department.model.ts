export interface IDepartment {
    id?: number;
    name?: string;
    shortName?: string;
    code?: string;
    employeeFullName?: string;
    employeeId?: number;
}

export class Department implements IDepartment {
    constructor(
        public id?: number,
        public name?: string,
        public shortName?: string,
        public code?: string,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
