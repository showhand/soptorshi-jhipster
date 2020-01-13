/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialPiService } from 'app/entities/commercial-pi/commercial-pi.service';
import { CommercialPi, CommercialPiStatus, ICommercialPi, PaymentType } from 'app/shared/model/commercial-pi.model';

describe('Service Tests', () => {
    describe('CommercialPi Service', () => {
        let injector: TestBed;
        let service: CommercialPiService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialPi;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialPiService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialPi(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                PaymentType.LC,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                CommercialPiStatus.WAITING_FOR_PI_APPROVAL_BY_THE_CUSTOMER,
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
                        proformaDate: currentDate.format(DATE_FORMAT),
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

            it('should create a CommercialPi', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        proformaDate: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        proformaDate: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialPi(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialPi', async () => {
                const returnedFromService = Object.assign(
                    {
                        companyName: 'BBBBBB',
                        companyDescription: 'BBBBBB',
                        proformaNo: 'BBBBBB',
                        proformaDate: currentDate.format(DATE_FORMAT),
                        harmonicCode: 'BBBBBB',
                        paymentType: 'BBBBBB',
                        paymentTerm: 'BBBBBB',
                        termsOfDelivery: 'BBBBBB',
                        shipmentDate: 'BBBBBB',
                        portOfLoading: 'BBBBBB',
                        portOfDestination: 'BBBBBB',
                        purchaseOrderNo: 'BBBBBB',
                        piStatus: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        proformaDate: currentDate,
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

            it('should return a list of CommercialPi', async () => {
                const returnedFromService = Object.assign(
                    {
                        companyName: 'BBBBBB',
                        companyDescription: 'BBBBBB',
                        proformaNo: 'BBBBBB',
                        proformaDate: currentDate.format(DATE_FORMAT),
                        harmonicCode: 'BBBBBB',
                        paymentType: 'BBBBBB',
                        paymentTerm: 'BBBBBB',
                        termsOfDelivery: 'BBBBBB',
                        shipmentDate: 'BBBBBB',
                        portOfLoading: 'BBBBBB',
                        portOfDestination: 'BBBBBB',
                        purchaseOrderNo: 'BBBBBB',
                        piStatus: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        proformaDate: currentDate,
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

            it('should delete a CommercialPi', async () => {
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
