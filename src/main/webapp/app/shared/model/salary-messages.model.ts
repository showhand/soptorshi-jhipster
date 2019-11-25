export interface ISalaryMessages {
    id?: number;
    comments?: any;
    commenterFullName?: string;
    commenterId?: number;
    monthlySalaryId?: number;
}

export class SalaryMessages implements ISalaryMessages {
    constructor(
        public id?: number,
        public comments?: any,
        public commenterFullName?: string,
        public commenterId?: number,
        public monthlySalaryId?: number
    ) {}
}
