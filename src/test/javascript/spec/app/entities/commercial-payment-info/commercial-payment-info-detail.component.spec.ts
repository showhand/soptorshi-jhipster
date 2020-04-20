/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPaymentInfoDetailComponent } from 'app/entities/commercial-payment-info/commercial-payment-info-detail.component';
import { CommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';

describe('Component Tests', () => {
    describe('CommercialPaymentInfo Management Detail Component', () => {
        let comp: CommercialPaymentInfoDetailComponent;
        let fixture: ComponentFixture<CommercialPaymentInfoDetailComponent>;
        const route = ({ data: of({ commercialPaymentInfo: new CommercialPaymentInfo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPaymentInfoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialPaymentInfoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPaymentInfoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialPaymentInfo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
