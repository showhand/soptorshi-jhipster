/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { JournalVoucherUpdateComponent } from 'app/entities/journal-voucher/journal-voucher-update.component';
import { JournalVoucherService } from 'app/entities/journal-voucher/journal-voucher.service';
import { JournalVoucher } from 'app/shared/model/journal-voucher.model';

describe('Component Tests', () => {
    describe('JournalVoucher Management Update Component', () => {
        let comp: JournalVoucherUpdateComponent;
        let fixture: ComponentFixture<JournalVoucherUpdateComponent>;
        let service: JournalVoucherService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [JournalVoucherUpdateComponent]
            })
                .overrideTemplate(JournalVoucherUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JournalVoucherUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalVoucherService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new JournalVoucher(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.journalVoucher = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new JournalVoucher();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.journalVoucher = entity;
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
