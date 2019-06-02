import { Designation } from 'app/shared/model/designation.model';
import { Office } from 'app/shared/model/office.model';

export interface IPayrollManagement {
    office?: Office;
    designation?: Designation;
}

export class PayrollManagement implements IPayrollManagement {
    constructor(public office?: Office, public designation?: Designation) {}
}
