/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { OverTimeUpdateComponent } from 'app/entities/over-time/over-time-update.component';
import { OverTimeService } from 'app/entities/over-time/over-time.service';
import { OverTime } from 'app/shared/model/over-time.model';

describe('Component Tests', () => {
    describe('OverTime Management Update Component', () => {
        let comp: OverTimeUpdateComponent;
        let fixture: ComponentFixture<OverTimeUpdateComponent>;
        let service: OverTimeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [OverTimeUpdateComponent]
            })
                .overrideTemplate(OverTimeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OverTimeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OverTimeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new OverTime(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.overTime = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new OverTime();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.overTime = entity;
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
