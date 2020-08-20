import { IConstantsModel } from 'app/shared/model/constants-model';

export const DATE_FORMAT = 'YYYY-MM-DD';
export const DATE_TIME_FORMAT = 'YYYY-MM-DDTHH:mm';
export const DAYS = [
    { id: 1, name: '01' },
    { id: 2, name: '02' },
    { id: 3, name: '03' },
    { id: 4, name: '04' },
    { id: 5, name: '05' },
    { id: 6, name: '06' },
    { id: 7, name: '07' },
    { id: 8, name: '08' },
    { id: 9, name: '09' },
    { id: 10, name: '10' },
    { id: 11, name: '11' },
    { id: 12, name: '12' },
    { id: 13, name: '13' },
    { id: 14, name: '14' },
    { id: 15, name: '15' },
    { id: 16, name: '16' },
    { id: 17, name: '17' },
    { id: 18, name: '18' },
    { id: 19, name: '19' },
    { id: 20, name: '20' },
    { id: 21, name: '21' },
    { id: 22, name: '22' },
    { id: 23, name: '23' },
    { id: 24, name: '24' },
    { id: 25, name: '25' },
    { id: 26, name: '26' },
    { id: 27, name: '27' },
    { id: 28, name: '28' },
    { id: 29, name: '29' },
    { id: 30, name: '30' },
    { id: 31, name: '31' }
];
export const MONTHS = [
    { id: 1, name: 'January' },
    { id: 2, name: 'February' },
    { id: 3, name: 'March' },
    { id: 4, name: 'April' },
    { id: 5, name: 'May' },
    { id: 6, name: 'June' },
    { id: 7, name: 'July' },
    { id: 8, name: 'August' },
    { id: 9, name: 'September' },
    { id: 10, name: 'October' },
    { id: 11, name: 'November' },
    { id: 12, name: 'December' }
];
export const YEARS = () => {
    const currentYear = new Date().getFullYear();
    const leastYear = 1962;
    const range: IConstantsModel[] = [];
    for (let i = currentYear; i > leastYear; i--) {
        let data: IConstantsModel = <IConstantsModel>{};
        data.id = i;
        data.name = i.toString();
        range.push(data);
    }
    return range;
};
