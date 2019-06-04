import { Office } from 'app/shared/model/office.model';
import { Designation } from 'app/shared/model/designation.model';

export interface IAllowanceManagement {
    office?: Office;
    designation?: Designation;
}

export class AllowanceManagement implements IAllowanceManagement {
    constructor(public office?: Office, public designation?: Designation) {}
}
