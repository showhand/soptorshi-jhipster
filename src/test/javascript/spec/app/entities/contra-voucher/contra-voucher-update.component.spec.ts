/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ContraVoucherUpdateComponent } from 'app/entities/contra-voucher/contra-voucher-update.component';
import { ContraVoucherService } from 'app/entities/contra-voucher/contra-voucher.service';
import { ContraVoucher } from 'app/shared/model/contra-voucher.model';

describe('Component Tests', () => {
    describe('ContraVoucher Management Update Component', () => {
        let comp: ContraVoucherUpdateComponent;
        let fixture: ComponentFixture<ContraVoucherUpdateComponent>;
        let service: ContraVoucherService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ContraVoucherUpdateComponent]
            })
                .overrideTemplate(ContraVoucherUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContraVoucherUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContraVoucherService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContraVoucher(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contraVoucher = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContraVoucher();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contraVoucher = entity;
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
