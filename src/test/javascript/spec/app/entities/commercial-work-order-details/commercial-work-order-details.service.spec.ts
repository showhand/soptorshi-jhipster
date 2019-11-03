/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialWorkOrderDetailsService } from 'app/entities/commercial-work-order-details/commercial-work-order-details.service';
import {
    CommercialCurrency,
    CommercialWorkOrderDetails,
    ICommercialWorkOrderDetails
} from 'app/shared/model/commercial-work-order-details.model';

describe('Service Tests', () => {
    describe('CommercialWorkOrderDetails Service', () => {
        let injector: TestBed;
        let service: CommercialWorkOrderDetailsService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialWorkOrderDetails;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialWorkOrderDetailsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialWorkOrderDetails(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                CommercialCurrency.BDT,
                0,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createOn: currentDate.format(DATE_FORMAT)
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

            it('should create a CommercialWorkOrderDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialWorkOrderDetails(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialWorkOrderDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        goods: 'BBBBBB',
                        reason: 'BBBBBB',
                        size: 'BBBBBB',
                        color: 'BBBBBB',
                        quantity: 1,
                        currencyType: 'BBBBBB',
                        rate: 1,
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createOn: currentDate
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

            it('should return a list of CommercialWorkOrderDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        goods: 'BBBBBB',
                        reason: 'BBBBBB',
                        size: 'BBBBBB',
                        color: 'BBBBBB',
                        quantity: 1,
                        currencyType: 'BBBBBB',
                        rate: 1,
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createOn: currentDate
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

            it('should delete a CommercialWorkOrderDetails', async () => {
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
