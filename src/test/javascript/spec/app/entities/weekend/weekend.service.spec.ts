/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { WeekendService } from 'app/entities/weekend/weekend.service';
import { DayOfWeek, IWeekend, Weekend, WeekendStatus } from 'app/shared/model/weekend.model';

describe('Service Tests', () => {
    describe('Weekend Service', () => {
        let injector: TestBed;
        let service: WeekendService;
        let httpMock: HttpTestingController;
        let elemDefault: IWeekend;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(WeekendService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Weekend(
                0,
                0,
                currentDate,
                currentDate,
                DayOfWeek.SUNDAY,
                DayOfWeek.SUNDAY,
                DayOfWeek.SUNDAY,
                WeekendStatus.ACTIVE,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        activeFrom: currentDate.format(DATE_FORMAT),
                        activeTo: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Weekend', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        activeFrom: currentDate.format(DATE_FORMAT),
                        activeTo: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        activeFrom: currentDate,
                        activeTo: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Weekend(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Weekend', async () => {
                const returnedFromService = Object.assign(
                    {
                        numberOfDays: 1,
                        activeFrom: currentDate.format(DATE_FORMAT),
                        activeTo: currentDate.format(DATE_FORMAT),
                        day1: 'BBBBBB',
                        day2: 'BBBBBB',
                        day3: 'BBBBBB',
                        status: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        activeFrom: currentDate,
                        activeTo: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Weekend', async () => {
                const returnedFromService = Object.assign(
                    {
                        numberOfDays: 1,
                        activeFrom: currentDate.format(DATE_FORMAT),
                        activeTo: currentDate.format(DATE_FORMAT),
                        day1: 'BBBBBB',
                        day2: 'BBBBBB',
                        day3: 'BBBBBB',
                        status: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        activeFrom: currentDate,
                        activeTo: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Weekend', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
