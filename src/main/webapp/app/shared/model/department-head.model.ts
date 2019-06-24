export interface IDepartmentHead {
    id?: number;
    departmentName?: string;
    departmentId?: number;
    employeeFullName?: string;
    employeeId?: number;
    officeName?: string;
    officeId?: number;
}

export class DepartmentHead implements IDepartmentHead {
    constructor(
        public id?: number,
        public departmentName?: string,
        public departmentId?: number,
        public employeeFullName?: string,
        public employeeId?: number,
        public officeName?: string,
        public officeId?: number
    ) {}
}
