import { Moment } from 'moment';

export const enum EmploymentType {
    PERMANENT = 'PERMANENT',
    TEMPORARY = 'TEMPORARY',
    ADHOC = 'ADHOC',
    PART_TIME = 'PART_TIME'
}

export interface IExperienceInformation {
    id?: number;
    organization?: string;
    designation?: string;
    startDate?: Moment;
    endDate?: Moment;
    employmentType?: EmploymentType;
    employeeId?: number;
}

export class ExperienceInformation implements IExperienceInformation {
    constructor(
        public id?: number,
        public organization?: string,
        public designation?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public employmentType?: EmploymentType,
        public employeeId?: number
    ) {}
}
