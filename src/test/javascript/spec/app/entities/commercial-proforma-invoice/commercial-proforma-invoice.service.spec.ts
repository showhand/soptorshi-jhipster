/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialProformaInvoiceService } from 'app/entities/commercial-proforma-invoice/commercial-proforma-invoice.service';
import { CommercialProformaInvoice, ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';

describe('Service Tests', () => {
    describe('CommercialProformaInvoice Service', () => {
        let injector: TestBed;
        let service: CommercialProformaInvoiceService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialProformaInvoice;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialProformaInvoiceService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialProformaInvoice(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
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
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedOn: currentDate.format(DATE_FORMAT)
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

            it('should create a CommercialProformaInvoice', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        proformaDate: currentDate.format(DATE_FORMAT),
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        proformaDate: currentDate,
                        createOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialProformaInvoice(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialProformaInvoice', async () => {
                const returnedFromService = Object.assign(
                    {
                        companyName: 'BBBBBB',
                        companyDescriptionOrAddress: 'BBBBBB',
                        proformaNo: 'BBBBBB',
                        proformaDate: currentDate.format(DATE_FORMAT),
                        harmonicCode: 'BBBBBB',
                        paymentTerm: 'BBBBBB',
                        termsOfDelivery: 'BBBBBB',
                        shipmentDate: 'BBBBBB',
                        portOfLanding: 'BBBBBB',
                        portOfDestination: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        proformaDate: currentDate,
                        createOn: currentDate,
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

            it('should return a list of CommercialProformaInvoice', async () => {
                const returnedFromService = Object.assign(
                    {
                        companyName: 'BBBBBB',
                        companyDescriptionOrAddress: 'BBBBBB',
                        proformaNo: 'BBBBBB',
                        proformaDate: currentDate.format(DATE_FORMAT),
                        harmonicCode: 'BBBBBB',
                        paymentTerm: 'BBBBBB',
                        termsOfDelivery: 'BBBBBB',
                        shipmentDate: 'BBBBBB',
                        portOfLanding: 'BBBBBB',
                        portOfDestination: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        proformaDate: currentDate,
                        createOn: currentDate,
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

            it('should delete a CommercialProformaInvoice', async () => {
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
