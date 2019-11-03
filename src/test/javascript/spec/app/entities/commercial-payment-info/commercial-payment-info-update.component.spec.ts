/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPaymentInfoUpdateComponent } from 'app/entities/commercial-payment-info/commercial-payment-info-update.component';
import { CommercialPaymentInfoService } from 'app/entities/commercial-payment-info/commercial-payment-info.service';
import { CommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';

describe('Component Tests', () => {
    describe('CommercialPaymentInfo Management Update Component', () => {
        let comp: CommercialPaymentInfoUpdateComponent;
        let fixture: ComponentFixture<CommercialPaymentInfoUpdateComponent>;
        let service: CommercialPaymentInfoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPaymentInfoUpdateComponent]
            })
                .overrideTemplate(CommercialPaymentInfoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialPaymentInfoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPaymentInfoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPaymentInfo(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPaymentInfo = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPaymentInfo();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPaymentInfo = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
