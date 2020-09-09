/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LeaveApplicationService } from 'app/entities/leave-application/leave-application.service';
import { ILeaveApplication, LeaveApplication, LeaveStatus, PaidOrUnPaid } from 'app/shared/model/leave-application.model';

describe('Service Tests', () => {
    describe('LeaveApplication Service', () => {
        let injector: TestBed;
        let service: LeaveApplicationService;
        let httpMock: HttpTestingController;
        let elemDefault: ILeaveApplication;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(LeaveApplicationService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new LeaveApplication(
                0,
                currentDate,
                currentDate,
                0,
                PaidOrUnPaid.PAID,
                'AAAAAAA',
                currentDate,
                currentDate,
                LeaveStatus.WAITING
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        appliedOn: currentDate.format(DATE_TIME_FORMAT),
                        actionTakenOn: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a LeaveApplication', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        appliedOn: currentDate.format(DATE_TIME_FORMAT),
                        actionTakenOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fromDate: currentDate,
                        toDate: currentDate,
                        appliedOn: currentDate,
                        actionTakenOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new LeaveApplication(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a LeaveApplication', async () => {
                const returnedFromService = Object.assign(
                    {
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        numberOfDays: 1,
                        paidLeave: 'BBBBBB',
                        reason: 'BBBBBB',
                        appliedOn: currentDate.format(DATE_TIME_FORMAT),
                        actionTakenOn: currentDate.format(DATE_TIME_FORMAT),
                        status: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fromDate: currentDate,
                        toDate: currentDate,
                        appliedOn: currentDate,
                        actionTakenOn: currentDate
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

            it('should return a list of LeaveApplication', async () => {
                const returnedFromService = Object.assign(
                    {
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        numberOfDays: 1,
                        paidLeave: 'BBBBBB',
                        reason: 'BBBBBB',
                        appliedOn: currentDate.format(DATE_TIME_FORMAT),
                        actionTakenOn: currentDate.format(DATE_TIME_FORMAT),
                        status: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fromDate: currentDate,
                        toDate: currentDate,
                        appliedOn: currentDate,
                        actionTakenOn: currentDate
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

            it('should delete a LeaveApplication', async () => {
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
