/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SupplyOrderDetailsService } from 'app/entities/supply-order-details/supply-order-details.service';
import { ISupplyOrderDetails, SupplyOrderDetails } from 'app/shared/model/supply-order-details.model';

describe('Service Tests', () => {
    describe('SupplyOrderDetails Service', () => {
        let injector: TestBed;
        let service: SupplyOrderDetailsService;
        let httpMock: HttpTestingController;
        let elemDefault: ISupplyOrderDetails;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(SupplyOrderDetailsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new SupplyOrderDetails(0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
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

            it('should create a SupplyOrderDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new SupplyOrderDetails(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a SupplyOrderDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT),
                        quantity: 1,
                        price: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
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

            it('should return a list of SupplyOrderDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT),
                        quantity: 1,
                        price: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
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

            it('should delete a SupplyOrderDetails', async () => {
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
