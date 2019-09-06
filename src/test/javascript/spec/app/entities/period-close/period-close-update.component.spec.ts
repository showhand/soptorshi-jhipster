/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PeriodCloseUpdateComponent } from 'app/entities/period-close/period-close-update.component';
import { PeriodCloseService } from 'app/entities/period-close/period-close.service';
import { PeriodClose } from 'app/shared/model/period-close.model';

describe('Component Tests', () => {
    describe('PeriodClose Management Update Component', () => {
        let comp: PeriodCloseUpdateComponent;
        let fixture: ComponentFixture<PeriodCloseUpdateComponent>;
        let service: PeriodCloseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PeriodCloseUpdateComponent]
            })
                .overrideTemplate(PeriodCloseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PeriodCloseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodCloseService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PeriodClose(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.periodClose = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PeriodClose();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.periodClose = entity;
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
