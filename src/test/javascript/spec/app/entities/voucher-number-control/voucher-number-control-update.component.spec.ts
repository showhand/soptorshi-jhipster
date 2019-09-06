/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { VoucherNumberControlUpdateComponent } from 'app/entities/voucher-number-control/voucher-number-control-update.component';
import { VoucherNumberControlService } from 'app/entities/voucher-number-control/voucher-number-control.service';
import { VoucherNumberControl } from 'app/shared/model/voucher-number-control.model';

describe('Component Tests', () => {
    describe('VoucherNumberControl Management Update Component', () => {
        let comp: VoucherNumberControlUpdateComponent;
        let fixture: ComponentFixture<VoucherNumberControlUpdateComponent>;
        let service: VoucherNumberControlService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [VoucherNumberControlUpdateComponent]
            })
                .overrideTemplate(VoucherNumberControlUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VoucherNumberControlUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherNumberControlService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new VoucherNumberControl(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.voucherNumberControl = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new VoucherNumberControl();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.voucherNumberControl = entity;
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
