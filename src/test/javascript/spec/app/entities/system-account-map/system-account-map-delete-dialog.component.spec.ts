/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SystemAccountMapDeleteDialogComponent } from 'app/entities/system-account-map/system-account-map-delete-dialog.component';
import { SystemAccountMapService } from 'app/entities/system-account-map/system-account-map.service';

describe('Component Tests', () => {
    describe('SystemAccountMap Management Delete Component', () => {
        let comp: SystemAccountMapDeleteDialogComponent;
        let fixture: ComponentFixture<SystemAccountMapDeleteDialogComponent>;
        let service: SystemAccountMapService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SystemAccountMapDeleteDialogComponent]
            })
                .overrideTemplate(SystemAccountMapDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SystemAccountMapDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SystemAccountMapService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
