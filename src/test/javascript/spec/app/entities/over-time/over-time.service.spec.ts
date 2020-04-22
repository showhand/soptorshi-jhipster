/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OverTimeService } from 'app/entities/over-time/over-time.service';
import { IOverTime, OverTime } from 'app/shared/model/over-time.model';

describe('Service Tests', () => {
    describe('OverTime Service', () => {
        let injector: TestBed;
        let service: OverTimeService;
        let httpMock: HttpTestingController;
        let elemDefault: IOverTime;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(OverTimeService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new OverTime(0, currentDate, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        overTimeDate: currentDate.format(DATE_FORMAT),
                        fromTime: currentDate.format(DATE_TIME_FORMAT),
                        toTime: currentDate.format(DATE_TIME_FORMAT),
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

            it('should create a OverTime', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        overTimeDate: currentDate.format(DATE_FORMAT),
                        fromTime: currentDate.format(DATE_TIME_FORMAT),
                        toTime: currentDate.format(DATE_TIME_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        overTimeDate: currentDate,
                        fromTime: currentDate,
                        toTime: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new OverTime(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a OverTime', async () => {
                const returnedFromService = Object.assign(
                    {
                        overTimeDate: currentDate.format(DATE_FORMAT),
                        fromTime: currentDate.format(DATE_TIME_FORMAT),
                        toTime: currentDate.format(DATE_TIME_FORMAT),
                        duration: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        overTimeDate: currentDate,
                        fromTime: currentDate,
                        toTime: currentDate,
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

            it('should return a list of OverTime', async () => {
                const returnedFromService = Object.assign(
                    {
                        overTimeDate: currentDate.format(DATE_FORMAT),
                        fromTime: currentDate.format(DATE_TIME_FORMAT),
                        toTime: currentDate.format(DATE_TIME_FORMAT),
                        duration: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        overTimeDate: currentDate,
                        fromTime: currentDate,
                        toTime: currentDate,
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

            it('should delete a OverTime', async () => {
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
